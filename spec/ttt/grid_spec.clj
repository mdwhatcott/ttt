(ns ttt.grid-spec
  (:require [speclj.core :refer :all]
            [ttt.grid :refer :all]))

(def no-winners
  [
   [_ _ _
    _ _ _
    _ _ _],

   [X _ _
    _ _ _
    _ _ _],

   [X X _
    _ _ _
    _ _ _],

   [_ X _
    _ _ _
    _ _ _],

   [_ _ X
    _ _ _
    _ _ _],

   [_ X X
    _ _ _
    _ _ _],

   [_ _ _
    X _ _
    _ _ _],

   [O _ _
    _ _ _
    _ _ _],

   [O O _
    _ _ _
    _ _ _],

   [_ O _
    _ _ _
    _ _ _],

   [_ _ O
    _ _ _
    _ _ _],

   [_ O O
    _ _ _
    _ _ _],

   [_ _ _
    O _ _
    _ _ _],

   ])

(def winners-x
  [
   [X X X
    _ _ _
    _ _ _],

   [_ _ _
    X X X
    _ _ _],

   [_ _ _
    _ _ _
    X X X],

   [X _ _
    X _ _
    X _ _],

   [_ X _
    _ X _
    _ X _],

   [_ _ X
    _ _ X
    _ _ X],

   [X _ _
    _ X _
    _ _ X],

   [_ _ X
    _ X _
    X _ _],

   [X O O
    O X X
    X O X],

   ])

(def winners-o
  [
   [O O O
    _ _ _
    _ _ _],

   [_ _ _
    O O O
    _ _ _],

   [_ _ _
    _ _ _
    O O O],

   [O _ _
    O _ _
    O _ _],

   [_ O _
    _ O _
    _ O _],

   [_ _ O
    _ _ O
    _ _ O],

   [O _ _
    _ O _
    _ _ O],

   [_ _ O
    _ O _
    O _ _],

   [X O O
    O O X
    X O X],

   ])

(describe "Grid Mechanics"

  (it "starts with 9 empty spots"
    (->> (make-grid) (should= [_ _ _
                               _ _ _
                               _ _ _])))

  (it "places Xs"
    (->> (make-grid)
         (place X 0)
         (should= [X _ _
                   _ _ _
                   _ _ _])))

  (it "places Os"
    (->> (make-grid)
         (place O 0)
         (should= [O _ _
                   _ _ _
                   _ _ _])))

  (it "preserves Xs"
    (->> (make-grid)
         (place X 0)
         (place O 0)
         (should= [X _ _
                   _ _ _
                   _ _ _])))

  (it "preserves Os"
    (->> (make-grid)
         (place O 0)
         (place X 0)
         (should= [O _ _
                   _ _ _
                   _ _ _])))

  (it "ignores out-of-bounds"
    (let [empty (make-grid)]
      (->> empty
           (place O (inc (count empty)))
           (should= empty)))))

(defn render [grid]
  (apply str (map #(if (nil? %) "-" %) grid)))

(describe "Winning Conditions"
  (for [grid no-winners]
    (it (str "identifies non-winning conditions " (render grid))
      (should-be-nil (winner grid))))

  (for [grid winners-x]
    (it (str "identifies winning conditions for X " (render grid))
      (should= X (winner grid))))

  (for [grid winners-o]
    (it (str "identifies winning conditions for O " (render grid))
      (should= O (winner grid)))))

(describe "Grid Scanning"
  (it "identifies all available spots on an empty grid"
    (->> (make-grid)
         available-cells
         (should= [0 1 2 3 4 5 6 7 8])))

  (it "identifies all available spots on a partially filled grid"
    (->> (make-grid)
         (place X 0)
         (place O 3)
         (place X 4)
         (place O 8)
         available-cells
         (should= [1 2 5 6 7])))

  (it "identifies no available spots on a filled-in grid"
    (->> [O X O
          X O O
          X O X]
         available-cells
         (should= []))))