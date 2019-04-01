(ns roomkey.handlers.hotel
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]))

(reg-event-fx
 :hotel/init
 ;; Might be better to just take a hash-map param.
 (fn [{:keys [db]} [_ hotel-id check-in check-out guests rooms]]
   {:http-xhrio {:method :get
                 :uri (str "http://localhost:3000/hotels/" hotel-id)
                 :params {:checkIn check-in
                          :checkOut check-out
                          :guests guests
                          :rooms rooms}
                 :timeout 5000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:hotel/set-hotel]
                 :on-failure [:util/set-error]}}))

(reg-event-db
 :hotel/set-hotel
 (fn [db [_ hotel]]
   (assoc-in db [:data :hotel] hotel)))
