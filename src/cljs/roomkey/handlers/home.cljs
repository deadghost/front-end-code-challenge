(ns roomkey.handlers.home
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]))

(reg-event-db
 :home/init
 (fn [db [_]]
   ;; This should be a merge or deep-merge.
   (-> (assoc-in db [:state :guest-quantity] 2)
       (assoc-in [:state :room-quantity] 1))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Search
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Field 1: Where are you going?
;;==============================================================================

(reg-event-fx
 :home/autofill-location
 (fn [{:keys [db]} [_ location-str]]
   {:http-xhrio {:method :get
                 :uri "http://localhost:3000/autofill"
                 :params {:query location-str}
                 :timeout 5000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:home/set-autofill-locations]
                 :on-failure [:util/set-error]}}))

(reg-event-db
 :home/set-autofill-locations
 (fn [db [_ {:keys [query locations airports]}]]
   (assoc-in db [:data :autofill-locations] locations)))

(reg-event-db
 :home/set-location
 (fn [db [_ location]]
   (assoc-in db [:state :location] location)))

;; Field 2: Check-in -> Check-out
;;==============================================================================

(reg-event-db
 :home/set-check-in-date
 (fn [db [_ date]]
   (assoc-in db [:state :check-in-date] date)))

(reg-event-db
 :home/set-check-out-date
 (fn [db [_ date]]
   (assoc-in db [:state :check-out-date] date)))

(reg-event-db
 :home/set-date-focused-input
 (fn [db [_ focused-input]]
   (assoc-in db [:state :date-focused-input] focused-input)))

;; Field 3: x Guests y Rooms
;;==============================================================================

(reg-event-db
 :home/set-guest-quantity
 (fn [db [_ quantity]]
   (assoc-in db [:state :guest-quantity]
             (if (< quantity 1) ;; Quantity can't be lower than 1.
               1
               quantity))))

(reg-event-db
 :home/set-room-quantity
 (fn [db [_ quantity]]
   (assoc-in db [:state :room-quantity]
             (if (< quantity 1) ;; Quantity can't be lower than 1.
               1
               quantity))))
