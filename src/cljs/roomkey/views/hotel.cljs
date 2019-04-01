(ns roomkey.views.hotel
  (:require
   [dommy.core :as dom :refer-macros [sel1]]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [roomkey.views.components.top-nav :refer [top-nav]]
   [roomkey.views.components.footer :refer [footer]]
   [roomkey.handlers.home]
   [roomkey.handlers.hotel]
   [roomkey.subs.hotel]
   [roomkey.subs.util]))

(defn hotel []
  (let [hotel-data (subscribe [:hotel/hotel])]
    [:div
     [top-nav]
     [:h2 (:name @hotel-data)]
     [:p (:description @hotel-data)]
     [footer]])) 

(defn hotel-page []
  (r/create-class
   {:display-name "hotel-page"
    :reagent-render hotel}))
