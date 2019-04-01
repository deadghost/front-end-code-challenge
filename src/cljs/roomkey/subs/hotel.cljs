(ns roomkey.subs.hotel
  (:require [re-frame.core :refer [register-sub]])
  (:require-macros [reagent.ratom :refer [reaction]]))

(register-sub
 :hotel/hotel
 (fn [db [_]]
   (reaction (get-in @db [:data :hotel]))))
