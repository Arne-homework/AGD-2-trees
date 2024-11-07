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
            0, // Default value
            0, // Lower bound
            null); // Upper bound
    
    /**
     * The level of the current Node.
     */
    public static final IProperty<Integer> NODE_LEVEL = new Property<Integer>("nodeLevel",
            0, // Default value
            0, // Lower bound
            null); // Upper bound
    
    /**
     * Flag, whether the current node is the root node or not.
     * True, if node == root. False, otherwise.
     */
    public static final IProperty<Boolean> IS_ROOT = new Property<Boolean>("isRoot",
            false,
            null,
            null);
    
}
