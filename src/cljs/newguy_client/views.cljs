(ns newguy-client.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]))

;; --------------------
(defn home-title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name ". This is the Home Page.")
       :level :level1])))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn allow-drop [e]
  (.preventDefault e))

(def circle-colors ["silver"
                    "purple"
                    "red"
                    "SkyBlue"
                    "mediumspringgreen"
                    "gold"])

(defn circle-color-picker [id]
  (nth circle-colors (- id 1)))

(defn animal-circle [animal]
  (let [{:keys [id name breed yard_id]} animal]
    [:div.animal.col-md-4.col-sm-2.col-xs-4
     {:id id
      :on-drag-start #(.setData (.-dataTransfer %) "text/plain" (str id))
      :draggable true
      :style {:background-color (circle-color-picker id)}}
     [:h5 name]
     [:p.text-center breed]
     [:span.badge {:style {:position "relative"
                           :right "-30px"
                           :bottom "5px"
                           :background-color "red"}} yard_id]]))

(defn generate-animal-circles [animals]
  (doall (for [animal animals]
    ^{:key (:id animal)} [animal-circle animal])))

(defn search-results []
  (let [filtered-animals (re-frame/subscribe [:filtered-animals])]
    (fn []
      [:div.text-center
       (if (empty? @filtered-animals)
         [:h3 "No Results"]
        (generate-animal-circles (take 6 @filtered-animals)))])))

(defn search-bar []
  [:div
   [:div.form-group
    [:label {:for "Search"}]
    [:input.form-control
     {:placeholder "Search"
      :on-change #(re-frame/dispatch
                   [:set-search-filter
                    (-> % .-target.value)])}]]])

(defn change-animal-yard [e yard_id]
  "drop event handler. The event contains the animal id
   as a string and it must be parsed before dispatch"
  (let [animal_id (js/parseInt (.getData (.-dataTransfer e) "text/plain"))]
    (re-frame/dispatch [:change-animal-yard animal_id yard_id])))

(defn yard [yard_id]
  (let [animals-in-yard (re-frame/subscribe [:animals-in-yard yard_id])
        yard-details    (re-frame/subscribe [:yard-by-id yard_id])]
    (fn [yard_id]
      [:div.text-center
       [:h4 (:name @yard-details)]
       [:div.well {:id "Yard"
                   :on-drag-enter allow-drop
                   :on-drag-over  allow-drop
                   :on-drop #(change-animal-yard % yard_id)
                   :style {:height "250px"
                           :width  "450px"}}
        (generate-animal-circles @animals-in-yard)]])))


(defn yard-component [yard]
  (let [yard-id (:id yard)
        animals-in-yard (re-frame/subscribe [:animals-in-yard (:id yard)])]
    (fn []
      [:div.btn.btn-primary {:on-click #(re-frame/dispatch [:set-active-yard yard-id])}
       (str (yard :name) " ")
       [:span.badge (count @animals-in-yard)]])))

(defn yard-list []
  (let [yards (re-frame/subscribe [:yards])]
    [:div
     (for [yard @yards]
       ^{:key (:id yard)} [yard-component yard])]))


(defn home-panel []
  (let [active-yard (re-frame/subscribe [:active-yard])]
  [:div.container-fluid
   [:div.row
    [:div.col-md-12.text-center
     [:h1 "New Guy!"
      [:h5 "Prototype"]]]]
   [:div.row
    [:div.col-md-12 [search-bar]]]
   [:div.row
    [:div.col-md-12.vertical-center {:style {:min-height "104px"}} [search-results]]]
   [:div.row
    [:div.col-md-12.vertical-center [yard @active-yard]]
   [:div.col-md-12.vertical-center [:div.btn.btn-danger {:on-drop #(change-animal-yard % nil)
                                                         :on-drag-over allow-drop
                                                         :on-drag-star allow-drop} "REMOVE FROM YARD"]]]

   [:div.row
    [:div.col-md-12.vertical-center [yard-list]]]]))


;; --------------------
(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; --------------------
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
       [:div.container (panels @active-panel)])))
