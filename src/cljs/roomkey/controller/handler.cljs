(ns roomkey.controller.handler
  (:require [bidi.bidi :as bidi]
            [dommy.core :as dom :refer-macros [sel1]]
            [reagent.core :as r]
            [re-frame.core :refer [dispatch dispatch-sync subscribe]]
            [roomkey.views.home :refer [home-page]]
            [roomkey.views.hotels :refer [hotels-page]]
            [roomkey.views.hotel :refer [hotel-page]]
            [roomkey.site.controller.routes :refer [routes]]
            [roomkey.handlers.home]
            [roomkey.handlers.util]
            [roomkey.util :refer
             [parse-query-params current-href current-pathname]]
            [pushy.core :as push :refer [pushy]]))

(def handler-table
  "Maps our route keywords to data and views."
  {:home
   {:data (fn [_params] [[:home/init]])
    :view [home-page]}
   :hotels
   {:data (fn [{:keys [query-params route-params]}]
            (let [{:keys [checkIn checkOut guests rooms]} query-params
                  {:keys [location-id]} route-params]
              [[:hotels/init
                location-id checkIn checkOut guests rooms]]))
    :view [hotels-page]}
   :hotel
   {:data (fn [{:keys [query-params route-params]}]
            (let [{:keys [checkIn checkOut guests rooms]} query-params
                  {:keys [hotel-id]} route-params]
              [[:hotel/init
                hotel-id checkIn checkOut guests rooms]]))
    :view [hotel-page]}})

(defn handle-route
  "href probably isn't the best thing to pass in since we end up using
  current-pathname as well."
  [href]
  (let [matched-route (bidi/match-route routes (current-pathname))
        params {:route-params (:route-params matched-route)
                :query-params (parse-query-params href)}
        handler-kw (:handler matched-route)
        handler-view (get-in handler-table [handler-kw :view])
        handler-data-fn (get-in handler-table [handler-kw :data])]
    (dispatch [:util/dispatch-n (handler-data-fn params)])
    (r/render handler-view (sel1 [:.mount-point]))))

(def history
  (pushy
   (fn [match]
     ;; Janks out when I use current-href but pathname shouldn't probably
     ;; get the query-params.
     (handle-route (current-href)))
   (partial bidi/match-route routes)))

(defn redirect [uri]
  (push/set-token! history uri))
