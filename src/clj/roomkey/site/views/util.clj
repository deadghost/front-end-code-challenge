(ns roomkey.site.views.util
  (:require [hiccup.core :as hiccup]))

(defn include-css
  "Function takes a href string to a CSS resource and produces a hiccup HTML
  hash-map."
  [href]
  [:link {:href href :rel "stylesheet"}])

(defn include-js
  "Function takes a src string to a JS resource and produces a hiccup HTML
   hash-map."
  [src]
  [:script {:type "text/javascript"
            :src src}])

(defn hiccup->html-resp
  ([hiccup-v]
   {:status 200
    :headers {"Content-Type" "text/html; charset=UTF-8"}
    :body (hiccup/html hiccup-v)})
  ([hiccup-v additional-params]
   (merge
    {:status 200
     :headers {"Content-Type" "text/html; charset=UTF-8"}
     :body (hiccup/html hiccup-v)}
    additional-params)))
