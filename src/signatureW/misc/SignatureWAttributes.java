package signatureW;

import mindustry.world.meta.Attribute;

public class SignatureWAttributes {
    public static Attribute ether;

    public static void load() {
        ether = Attribute.add("ether");
    }
}
