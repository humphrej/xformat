package formatter

import (
	"fmt"
	"os"
	"path/filepath"
	"sync"
)

// Responsible for walking and applying ignore_directories
func ParallelFileWalk(numParallel int, roots []string, ignoredDirectories []string, fn func(string, int, int)) error {

	c := make(chan string)
	var w sync.WaitGroup
	w.Add(numParallel)

	for i := 1; i <= numParallel; i++ {
		go func(i int, ci <-chan string) {
			j := 1
			for v := range ci {
				fn(v, i, j)
				j += 1
			}
			w.Done()
		}(i, c)
	}

	ignoredSet := make(map[string]bool)
	for _, v := range ignoredDirectories {
		ignoredSet[v] = true
	}

	for _, root := range roots {
		err := filepath.Walk(root, func(path string, info os.FileInfo, err error) error {
			if info == nil {
				// root does not exist
				return nil
			}
			if info.IsDir() && ignoredSet[info.Name()] {
				fmt.Printf("Ignoring directory: %+v \n", info.Name())
				return filepath.SkipDir
			}
			if !info.IsDir() {
				c <- path
			}
			return nil
		})
		if err != nil {
			return err
		}
	}

	close(c)
	w.Wait()
	return nil
}
