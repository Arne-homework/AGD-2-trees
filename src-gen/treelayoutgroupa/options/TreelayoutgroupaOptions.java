package treelayoutgroupa.options;

import org.eclipse.elk.core.AbstractLayoutProvider;
import org.eclipse.elk.core.data.ILayoutMetaDataProvider;
import org.eclipse.elk.core.data.LayoutAlgorithmData;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import treelayoutgroupa.TreelayoutgroupaLayoutProvider;
import treelayoutgroupa.TreelayoutgroupaMetadataProvider;

@SuppressWarnings("all")
public class TreelayoutgroupaOptions implements ILayoutMetaDataProvider {
  /**
   * The id of the Treelayoutgroupa algorithm.
   */
  public static final String ALGORITHM_ID = "treelayoutgroupa.Treelayoutgroupa";

  /**
   * True if nodes should be placed in reverse order of their
   * appearance in the graph.
   */
  public static final IProperty<Boolean> REVERSE_INPUT = TreelayoutgroupaMetadataProvider.REVERSE_INPUT;

  /**
   * Default value for {@link #PADDING} with algorithm "Treelayoutgroupa".
   */
  private static final ElkPadding PADDING_DEFAULT = new ElkPadding(10);

  /**
   * The padding to be left to a parent element's border when placing child elements. This can
   * also serve as an output option of a layout algorithm if node size calculation is setup
   * appropriately.
   */
  public static final IProperty<ElkPadding> PADDING = new Property<ElkPadding>(
                                CoreOptions.PADDING,
                                PADDING_DEFAULT);

  /**
   * Default value for {@link #SPACING_EDGE_EDGE} with algorithm "Treelayoutgroupa".
   */
  private static final double SPACING_EDGE_EDGE_DEFAULT = 5;

  /**
   * Spacing to be preserved between any two edges. Note that while this can somewhat easily be satisfied
   * for the segments of orthogonally drawn edges, it is harder for general polylines or splines.
   */
  public static final IProperty<Double> SPACING_EDGE_EDGE = new Property<Double>(
                                CoreOptions.SPACING_EDGE_EDGE,
                                SPACING_EDGE_EDGE_DEFAULT);

  /**
   * Default value for {@link #SPACING_EDGE_NODE} with algorithm "Treelayoutgroupa".
   */
  private static final double SPACING_EDGE_NODE_DEFAULT = 10;

  /**
   * Spacing to be preserved between nodes and edges.
   */
  public static final IProperty<Double> SPACING_EDGE_NODE = new Property<Double>(
                                CoreOptions.SPACING_EDGE_NODE,
                                SPACING_EDGE_NODE_DEFAULT);

  /**
   * Default value for {@link #SPACING_NODE_NODE} with algorithm "Treelayoutgroupa".
   */
  private static final double SPACING_NODE_NODE_DEFAULT = 10;

  /**
   * The minimal distance to be preserved between each two nodes.
   */
  public static final IProperty<Double> SPACING_NODE_NODE = new Property<Double>(
                                CoreOptions.SPACING_NODE_NODE,
                                SPACING_NODE_NODE_DEFAULT);

  /**
   * Layouter-specific algorithm factory.
   */
  public static class TreelayoutgroupaFactory implements org.eclipse.elk.core.util.IFactory<AbstractLayoutProvider> {
    public AbstractLayoutProvider create() {
      AbstractLayoutProvider provider = new TreelayoutgroupaLayoutProvider();
      provider.initialize("");
      return provider;
    }

    public void destroy(final AbstractLayoutProvider obj) {
      obj.dispose();
    }
  }

  public void apply(final org.eclipse.elk.core.data.ILayoutMetaDataProvider.Registry registry) {
    registry.register(new LayoutAlgorithmData.Builder()
        .id("treelayoutgroupa.Treelayoutgroupa")
        .name("Treelayoutgroupa")
        .description("Please insert a short but informative description here")
        .providerFactory(new TreelayoutgroupaFactory())
        .melkBundleName(null)
        .definingBundleId("treelayoutgroupa")
        .create()
    );
    registry.addOptionSupport(
        "treelayoutgroupa.Treelayoutgroupa",
        "treelayoutgroupa.reverseInput",
        REVERSE_INPUT.getDefault()
    );
    registry.addOptionSupport(
        "treelayoutgroupa.Treelayoutgroupa",
        "org.eclipse.elk.padding",
        PADDING_DEFAULT
    );
    registry.addOptionSupport(
        "treelayoutgroupa.Treelayoutgroupa",
        "org.eclipse.elk.spacing.edgeEdge",
        SPACING_EDGE_EDGE_DEFAULT
    );
    registry.addOptionSupport(
        "treelayoutgroupa.Treelayoutgroupa",
        "org.eclipse.elk.spacing.edgeNode",
        SPACING_EDGE_NODE_DEFAULT
    );
    registry.addOptionSupport(
        "treelayoutgroupa.Treelayoutgroupa",
        "org.eclipse.elk.spacing.nodeNode",
        SPACING_NODE_NODE_DEFAULT
    );
  }
}
