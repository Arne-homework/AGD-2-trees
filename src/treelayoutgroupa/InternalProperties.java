package treelayoutgroupa;

import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;

/**
 * Custom internal properties.
 */
public class InternalProperties {
    /**
     * The maximum height of the tree.
     */
    public static final IProperty<Integer> MAX_HEIGHT = new Property<Integer>("maxHeight",
            0,
            0, // Lower bound
            null); // Upper bound
    
    /**
     * The level of the current Node.
     */
    public static final IProperty<Integer> NODE_LEVEL = new Property<Integer>("nodeLevel",
            0,
            0, // Lower bound
            null); // Upper bound
}
