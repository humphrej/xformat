(defn transform* [person] (-> person
                              (assoc :hair-color :gray)
                              (update :age inc)))

(defn transform* [person]
  (-> person
      (assoc :hair-color :gray)
      (update :age inc)))


