package main.entity;

public enum EntityID
{
    Player(0),
    MiniBitingSlime(1),
    BitingSlime(2),
    Spider(3),
    VenomousSpider(4),
    Zombie(5);

    public final int ID;

    EntityID(int ID) {this.ID = ID;}
}
