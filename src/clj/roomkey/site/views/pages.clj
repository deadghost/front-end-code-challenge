(ns roomkey.site.views.pages
  (:require [roomkey.site.views.util :refer [hiccup->html-resp]]))

(defn index [req]
  (hiccup->html-resp
   [:html
    [:head
     [:title "Room Key - Best Hotel Rates"]
     [:meta {:charset "UTF-8"}]
     #_(include-css "/css/app.css")]
    [:body
     [:div {:class "mount-point"}]
     [:p "Hello"]
     #_(include-js "/js/app.js")]]))
