;; File diff
;
; * slurp
;
;; what to do next?
;
; * make diff algorithm smarter


(ns file_diff
   (:require [clojure.java.io :as io]
             [clojure.string :as s]
             )
   (:gen-class))


(defn read-file
  [file-to-read]
  (with-open [rdr (io/reader file-to-read)]
                  (doall (line-seq rdr)))
  )


(defn diff-files
  [file1 file2]
  ___
)

(diff-files file1 file2)
