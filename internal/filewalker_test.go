package formatter

import (
	"fmt"
	"io/ioutil"
	"os"
	"reflect"
	"sync"
	"testing"
)

func TestWalker(t *testing.T) {

	tempDir, err := ioutil.TempDir(os.TempDir(), "filewalker_test.*")

	err = makeTestData(tempDir)
	if err != nil {
		t.Fatalf("Error thrown %v", err)
	}

	collectorMap := sync.Map{}

	err = ParallelFileWalk(1, []string{tempDir + "/root1", tempDir + "/root_not_there"},
		[]string{"ignored"},
		func(fileName string, workerId int, workerTaskNum int) {
			collectorMap.Store(fileName, true)
		})

	if err != nil {
		t.Fatalf("walk failed %v", err)
	}

	expected := map[string]bool{
		tempDir + "/root1/a1.txt":        true,
		tempDir + "/root1/level2/a2.txt": true,
		// anything below ignored .. is ignored
	}

	actual := toRegularMap(collectorMap)

	if !reflect.DeepEqual(expected, actual) {
		t.Fatalf("ASSERTION FAILED %v %v", expected, actual)
	}
}

// Copies a sync.Map to a regular map
func toRegularMap(collectorMap sync.Map) map[string]bool {
	actual := map[string]bool{}
	collectorMap.Range(func(key, value interface{}) bool {
		var filename = fmt.Sprintf("%v", key)
		actual[filename] = true
		return true
	})
	return actual
}

func makeTestData(dir string) error {
	err := os.Mkdir(dir+"/root1", 0700)
	if err != nil {
		return err
	}
	err = ioutil.WriteFile(dir+"/root1/a1.txt", []byte{}, 0600)
	if err != nil {
		return err
	}
	err = os.Mkdir(dir+"/root1/level2", 0700)
	if err != nil {
		return err
	}
	err = ioutil.WriteFile(dir+"/root1/level2/a2.txt", []byte{}, 0600)
	if err != nil {
		return err
	}
	err = os.Mkdir(dir+"/root1/ignored", 0700)
	if err != nil {
		return err
	}
	err = ioutil.WriteFile(dir+"/root1/ignored/b.txt", []byte{}, 0600)
	if err != nil {
		return err
	}
	return nil
}
