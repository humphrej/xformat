package main

import (
	"flag"
	"fmt"
	"github.com/golang/glog"
	formatter "github.com/humphrej/xformat/internal"
	"os"
	"runtime"
	"strings"
)

type strslice []string

func (i *strslice) String() string {
	return strings.Join(*i, ",")
}

func (i *strslice) Set(value string) error {
	ss := strings.Split(value, ",")
	*i = append(*i, ss...)
	return nil
}

var (
	ignoreDirectories strslice
)

func main() {
	flag.Var(&ignoreDirectories, "ignore_directories", "Ignored directories, split by commas")
	flag.Parse()

	glog.V(1).Infof("Command args: %v", flag.Args())

	if len(flag.Args()) == 0 {
		flag.PrintDefaults()
		os.Exit(1)
	}

	formatter.ParallelFileWalk(runtime.NumCPU(),
		flag.Args(), ignoreDirectories,
		formatter.MatchFormatterFn(
			formatter.FormatFile,
		))
}

func Echo(file string, format formatter.Format) {
	fmt.Printf("%v %s\n", format, file)
}
