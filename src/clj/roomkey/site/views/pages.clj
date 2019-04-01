(ns roomkey.site.views.pages
  (:require [roomkey.site.views.util
             :refer [hiccup->html-resp include-css include-js]]))

(defn home [req]
  (hiccup->html-resp
   [:html
    [:head
     [:title "Room Key - Best Hotel Rates"]
     [:meta {:charset "UTF-8"}]
     (include-css
      "https://cdn.jsdelivr.net/npm/tailwindcss/dist/tailwind.min.css")
     (include-css
      "/css/react-dates/_datepicker.css")
     (include-css "/css/stylesheet.css")]
    [:body
     [:div {:class "mount-point"}]
     (include-js "/js/main.js")]]))
