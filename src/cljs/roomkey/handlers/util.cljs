(ns roomkey.handlers.util
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]))

(reg-event-fx
 :util/dispatch-n
 (fn [_ [_ events]]
   {:dispatch-n events}))

(reg-event-db
 :util/set-error
 (fn [db [_ error]]
   (assoc-in db [:state :error] error)))
