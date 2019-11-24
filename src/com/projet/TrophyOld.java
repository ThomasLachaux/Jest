package com.projet;

/*
joker
4 de pique
4 de trefle

 */
public class TrophyOld extends Card {

    private Color trophyColor;
    public enum Type {Highest, Lowest, Joker, Majority, BestJest, NoJoke}
    private Type type;

    public TrophyOld(Card card) {
        super(card.getColor(), card.getValue());
        assignTrophy();
    }

    private void assignTrophy() {

        switch (value) {
            case 0:
                type = Type.BestJest;
                break;

            case 1:
                switch (color) {
                    case Heart:
                        type = Type.Joker;
                        break;

                    case Spade:
                        type = Type.Highest;
                        trophyColor = Color.Club;
                        break;

                    case Club:
                        type = Type.Highest;
                        trophyColor = Color.Spade;
                        break;

                    case Diamond:
                        type = Type.Majority;
                }
                break;
        }

        if(color == Color.Jocker)
            type = Type.BestJest;

        else if (value == 4) {
            if(color == Color.Spade) {
                type = Type.Lowest;
                trophyColor = Color.Club;
            }

            else if(color == Color.Club) {
                type = Type.Lowest;
                trophyColor = Color.Spade;
            }
        }


    }

}
