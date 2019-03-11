(ns roomkey.site.views.pages
  (:require [roomkey.site.views.util
             :refer [hiccup->html-resp include-css include-js]]))

(defn index [req]
  (hiccup->html-resp
   [:html
    [:head
     [:title "Room Key - Best Hotel Rates"]
     [:meta {:charset "UTF-8"}]
     (include-css
      "https://cdn.jsdelivr.net/npm/tailwindcss/dist/tailwind.min.css")
     #_(include-css "/css/app.css")]
    [:body
     [:div {:class "mount-point"}]
     (include-js "/js/app.js")]]))
