(ns roomkey.views.components.footer)

(defn footer []
  [:nav 
   [:div {:class ["flex"]}
    [:a {:class ["p-4"]} "Home"]
    [:a {:class ["p-4"]} "Travel Blog"]
    [:a {:class ["p-4"]} "About Us"]
    [:a {:class ["p-4"]} "Careers"]
    [:a {:class ["p-4"]} "Terms of Use"]
    [:a {:class ["p-4"]} "Privacy Statement"]
    [:a {:class ["p-4"]} "Download Scout"]]
   [:div
    [:a {:class ["p-4"]}
     [:img {:src (str "https://d75rs6siber1y.cloudfront.net/ea590c2680/"
                      "jakarta/main_site/img/facebook-legacy.svg")}]]
    [:a {:class ["p-4"]}
     [:img {:src (str "https://d75rs6siber1y.cloudfront.net/ea590c2680/"
                      "jakarta/main_site/img/instagram-legacy.svg")}]]
    [:a {:class ["p-4"]}
     [:img {:src (str "https://d75rs6siber1y.cloudfront.net/ea590c2680/"
                      "jakarta/main_site/img/twitter-legacy.svg")}]]]
   [:div [:span {:class ["p-4"]} "© 2019"]]])
