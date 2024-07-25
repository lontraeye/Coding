package org.example;

public class Outfit {

    // Valores padrão
    private static final int DEFAULT_LOOKTYPE = 130;
    private static final int DEFAULT_ADDONS = 0;
    private static final int DEFAULT_MOVEMENT = 1;
    private static final int DEFAULT_DIRECTION = 3;
    private static final int DEFAULT_MOUNT = 322;
    private static final String DEFAULT_HEAD = "FFFFFF";
    private static final String DEFAULT_BODY = "FFFFFF";
    private static final String DEFAULT_LEGS = "FFFFFF";
    private static final String DEFAULT_FEET = "FFFFFF";
    private static final boolean DEFAULT_HEXMOUNT = false;

    // Atributos do outfit
    private int looktype;
    private int addons;
    private int movement;
    private int direction;
    private int mount;

    private String head;
    private String body;
    private String legs;
    private String feet;

    private boolean hexmount;

    // Construtor padrão que inicializa com valores padrão
    public Outfit() {
        this.looktype = DEFAULT_LOOKTYPE;
        this.addons = DEFAULT_ADDONS;
        this.movement = DEFAULT_MOVEMENT;
        this.direction = DEFAULT_DIRECTION;
        this.mount = DEFAULT_MOUNT;
        this.head = DEFAULT_HEAD;
        this.body = DEFAULT_BODY;
        this.legs = DEFAULT_LEGS;
        this.feet = DEFAULT_FEET;
        this.hexmount = DEFAULT_HEXMOUNT;
    }

    // Construtor com parâmetros
    public Outfit(int looktype, int addons, int movement, int direction, int mount, 
                  String head, String body, String legs, String feet, boolean hexmount) {
        this.looktype = looktype;
        this.addons = addons;
        this.movement = movement;
        this.direction = direction;
        this.mount = mount;
        this.head = head;
        this.body = body;
        this.legs = legs;
        this.feet = feet;
        this.hexmount = hexmount;
    }

    // Getters e setters
    public int getLooktype() {
        return looktype;
    }

    public void setLooktype(int looktype) {
        this.looktype = looktype;
    }

    public int getAddons() {
        return addons;
    }

    public void setAddons(int addons) {
        this.addons = addons;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public boolean isHexmount() {
        return hexmount;
    }

    public void setHexmount(boolean hexmount) {
        this.hexmount = hexmount;
    }

    @Override
    public String toString() {
        return "Outfit{" +
                "looktype=" + looktype +
                ", addons=" + addons +
                ", movement=" + movement +
                ", direction=" + direction +
                ", mount=" + mount +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                ", legs='" + legs + '\'' +
                ", feet='" + feet + '\'' +
                ", hexmount=" + hexmount +
                '}';
    }
}