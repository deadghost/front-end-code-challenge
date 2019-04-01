(ns roomkey.subs.home
  (:require [re-frame.core :refer [register-sub]])
  (:require-macros [reagent.ratom :refer [reaction]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Search
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Field 1: Where are you going?
;;==============================================================================

(register-sub
 :home/autofill-locations
 (fn [db [_]]
   (reaction (get-in @db [:data :autofill-locations]))))

(register-sub
 :home/location
 (fn [db [_]]
   (reaction (get-in @db [:state :location]))))

;; Field 2: Check-in -> Check-out
;;==============================================================================

(register-sub
 :home/check-in-date
 (fn [db [_]]
   (reaction (get-in @db [:state :check-in-date]))))

(register-sub
 :home/check-out-date
 (fn [db [_]]
   (reaction (get-in @db [:state :check-out-date]))))

(register-sub
 :home/date-focused-input
 (fn [db [_]]
   (reaction (get-in @db [:state :date-focused-input]))))

;; Field 3: x Guests y Rooms
;;==============================================================================

(register-sub
 :home/guest-quantity
 (fn [db [_]]
   (reaction (get-in @db [:state :guest-quantity]))))

(register-sub
 :home/room-quantity
 (fn [db [_]]
   (reaction (get-in @db [:state :room-quantity]))))
