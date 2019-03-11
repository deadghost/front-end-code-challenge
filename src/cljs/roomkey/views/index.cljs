(ns roomkey.views.index
  (:require [reagent.core :as r]))

(defn index []
  [:p "Hello, World!"])

(defn index-page []
  (r/create-class
   {:display-name "index-page"
    :reagent-render index}))
