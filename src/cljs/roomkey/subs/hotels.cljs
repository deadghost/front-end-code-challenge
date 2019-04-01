(ns roomkey.subs.hotels
  (:require [re-frame.core :refer [register-sub]])
  (:require-macros [reagent.ratom :refer [reaction]]))

(register-sub
 :hotels/hotels
 (fn [db [_]]
   (reaction (get-in @db [:data :hotels]))))
