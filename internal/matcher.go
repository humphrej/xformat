package formatter

import (
	"fmt"
	"github.com/golang/glog"
	"os/exec"
	"path/filepath"
	"regexp"
	"strings"
)

type MatcherSetup struct {
	name       Format
	matcherFns []func(f string) bool
}

type CommandSetup struct {
	Command string
	Args    string
}

var (
	matchers = []MatcherSetup{
		{name: JAVA,
			matcherFns: []func(string) bool{matchesExt(".java")}},
		{name: DHALL,
			matcherFns: []func(string) bool{matchesExt(".dhall")}},
		{name: SH,
			matcherFns: []func(string) bool{matchesExt(".sh")}},
		{name: BAZEL,
			matcherFns: []func(string) bool{
				matchesExt(".bazel"),
				matchesExt(".bzl"),
				matchesBase("WORKSPACE"),
				matchesBase("BUILD")}},
		{name: CPP,
			matcherFns: []func(string) bool{
				matchesExt(".cc"),
				matchesExt(".cpp"),
			}},
		{name: TYPESCRIPT,
			matcherFns: []func(string) bool{matchesExt(".ts")}},
		{name: PYTHON,
			matcherFns: []func(string) bool{matchesExt(".py")}},
		{name: GO,
			matcherFns: []func(string) bool{matchesExt(".go")}},
	}

	commands = map[Format]CommandSetup{
		JAVA:       {Command: "java", Args: "-jar /opt/formatters/bin/google-java-format.jar -r %s"},
		SH:         {Command: "shfmt", Args: "-w %s"},
		BAZEL:      {Command: "buildifier", Args: "-mode=fix %s"},
		CPP:        {Command: "clang-format", Args: "-i %s"},
		TYPESCRIPT: {Command: "tsfmt", Args: "--no-tslint -r %s"},
		DHALL:      {Command: "dhall", Args: "format --inplace %s"},
		PYTHON:     {Command: "yapf", Args: "-i %s"},
		GO:         {Command: "go", Args: "fmt %s"},
	}
)

func matchesExt(expectedExt string) func(f string) bool {
	return func(f string) bool {
		return filepath.Ext(f) == expectedExt
	}
}

func matchesBase(expected string) func(f string) bool {
	return func(f string) bool {
		return filepath.Base(f) == expected
	}
}

func matchesRegexp(expected *regexp.Regexp) func(f string) bool {
	return func(f string) bool {
		return expected.MatchString(f)
	}
}

type Format int

const (
	JAVA Format = iota
	BAZEL
	DHALL
	GO
	SH
	CPP
	TYPESCRIPT
	PYTHON
)

/**
Builds a matcher function that will call formatFn if it matches a filename
*/
func MatchFormatterFn(formatFn func(string, Format)) func(fileName string, workerId int, workerTaskNum int) {
	return func(fileName string, workerId int, workerTaskNum int) {
		for _, f := range matchers {
			for _, fn := range f.matcherFns {
				if fn(fileName) {
					formatFn(fileName, f.name)
				}
			}
		}
	}
}

func FormatFile(file string, format Format) {
	commandSetup := commands[format]
	args := strings.Split(fmt.Sprintf(commandSetup.Args, file), " ")

	cmd := exec.Command(commandSetup.Command, args...)
	glog.V(1).Infof("Formatting file %s using cmd: %v", file, cmd)
	err := cmd.Run()
	if err != nil {
		glog.Fatalf("Failed to format %s %v %v", file, format, err)
	}
}
