;; Github - top 10 repos
;
; * clj-http.client
;
;; what to do next?
; * query another resource from API
; * add tags to those top-10 repos
; * calculate tag distribution for clojure projects


(ns top-n
   (:require [clj-http.client :as client])
   (:gen-class))

(def github-url "https://api.github.com/search/repositories?q=language:clojure&sort=stars")

(defn get-top-repos [url count]
  ___)


(println (get-top-repos github-url 10))
