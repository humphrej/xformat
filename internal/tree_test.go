package formatter

import (
	"io/ioutil"
	"os"
	"path/filepath"
	"reflect"
	"testing"
)

func appendFilesFunc(files *[]string) func(path string, info os.FileInfo, err error) error {
	return func(path string, info os.FileInfo, err error) error {

		if !info.IsDir() {
			*files = append(*files, path)
		}
		return nil
	}
}
func treeEquals(root1 string, root2 string) (bool, error) {

	files1 := []string{}
	filepath.Walk(root1, appendFilesFunc(&files1))
	files2 := []string{}
	filepath.Walk(root2, appendFilesFunc(&files2))

	if ! reflect.DeepEqual(files1, files2) {
		return false, nil
	}

	// now we know all the files exist, compare the contents

	for i, _ := range files1 {
		bytes1, err := ioutil.ReadFile(files1[i])
		if err != nil {
			return false, err
		}
		bytes2, err := ioutil.ReadFile(files2[i])
		if err != nil {
			return false, err
		}

		if !reflect.DeepEqual(bytes1, bytes2) {
			return false, nil
		}
	}

	return true, nil
}

func TestTreeEquals(t *testing.T) {
	ret, err := treeEquals("test/data", "test/data")
	if err != nil {
		t.Fatal(err)
	}
	if !ret {
		t.Fatalf("Should be equal")
	}
}
