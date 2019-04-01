(ns roomkey.util
  (:require [clojure.string :as str]))

(defn query-params-str
  "Returns query params string."
  [path]
  (last (str/split path #"\?")))

(defn parse-query-params
  "Parse URL query parameters into a hashmap."
  [path]
  (let [param-strs (str/split (query-params-str path) #"\&")]
    (into {} (for [[k v] (map #(str/split % #"=") param-strs)]
               [(keyword k) v]))))

(defn current-pathname []
  js/window.location.pathname)

(defn current-href []
  js/window.location.href)
