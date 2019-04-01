(ns roomkey.handlers.hotels
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]))

(reg-event-fx
 :hotels/init
 ;; Might be better to just take a hash-map param.
 (fn [{:keys [db]} [_ location-id check-in check-out guests rooms]]
   {:http-xhrio {:method :get
                 :uri (str "http://localhost:3000/locations/" location-id
                           "/hotels")
                 :params {:checkIn check-in
                          :checkOut check-out
                          :guests guests
                          :rooms rooms}
                 :timeout 5000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:hotels/set-hotels]
                 :on-failure [:util/set-error]}}))

(reg-event-db
 :hotels/set-hotels
 (fn [db [_ hotels]]
   (assoc-in db [:data :hotels] (:data hotels))))
