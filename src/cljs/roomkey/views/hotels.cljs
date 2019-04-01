(ns roomkey.views.hotels
  (:require
   [dommy.core :as dom :refer-macros [sel1]]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [roomkey.views.components.top-nav :refer [top-nav]]
   [roomkey.views.components.footer :refer [footer]]
   [roomkey.handlers.home]
   [roomkey.subs.home]
   [roomkey.subs.hotels]
   [roomkey.subs.util]
   [roomkey.util :refer [query-params-str current-href]]))

(defn hotel [data]
  [:a {:class ["w-2/5" "m-2" "p-2" "bg-grey-lighter" "h-32" "inline-block"
               "align-top" "text-black" "no-underline"]
       :href (str "/hotels/" (:udicode data) "?"
                  (query-params-str (current-href)))}
   [:h2 (:name data)]])

(defn hotels []
  (let [hotels (subscribe [:hotels/hotels])]
    [:div
     [top-nav]
     [:div {:class ["content-around" "flex" "flex-wrap"]}
      (when @hotels
        (doall
         (for [hotel-data @hotels]
           ^{:key (:udicode hotel-data)}
           [hotel hotel-data])))]
     [:p (str @hotels)]])) 

(defn hotels-page []
  (r/create-class
   {:display-name "hotels-page"
    :reagent-render hotels}))
