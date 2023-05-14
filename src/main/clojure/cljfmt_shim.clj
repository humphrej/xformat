(ns cljfmt-shim
  "Shim for cljfmt"
  (:require
   [cljfmt.core :as cljfmt])
  (:gen-class
   :name xformat.cljfmtshim
   :methods [#^{:static true} [cljfmtshim [String] String]]))

(defn -cljfmtshim [s]
  ((cljfmt/wrap-normalize-newlines #(cljfmt/reformat-string % {})) s))

(def x 3)





