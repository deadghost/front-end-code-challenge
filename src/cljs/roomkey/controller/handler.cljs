(ns roomkey.controller.handler
  (:require [bidi.bidi :as bidi]
            [dommy.core :as dom :refer-macros [sel1]]
            [reagent.core :as r]
            [re-frame.core :refer [dispatch dispatch-sync subscribe]]
            [roomkey.views.index :refer [index-page]]
            [roomkey.site.controller.routes :refer [routes]]
            ;; [roomkey.handlers]
            [pushy.core :as push :refer [pushy]]))

(def handler-table
  "Maps our route keywords to data and views."
  {:index
   {:data nil
    :view [index-page]}})

(defn handle-route [pathname]
  (let [matched-route (bidi/match-route routes pathname)
        params (:route-params matched-route)
        handler-kw (:handler matched-route)
        handler-view (get-in handler-table [handler-kw :view])
        handler-data (get-in handler-table [handler-kw :data])]
    ;; (dispatch [:app/dispatch-n handler-data])
    (r/render handler-view (sel1 [:.mount-point]))))

(defn current-pathname []
  js/window.location.pathname)

(def history
  (pushy
   (fn [match]
     (handle-route (current-pathname)))
   (partial bidi/match-route routes)))

(defn redirect [uri]
  (push/set-token! history uri))
