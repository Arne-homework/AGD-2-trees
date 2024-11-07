package treelayoutgroupa;

import java.util.EnumSet;
import org.eclipse.elk.core.data.ILayoutMetaDataProvider;
import org.eclipse.elk.core.data.LayoutOptionData;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;

@SuppressWarnings("all")
public class TreelayoutgroupaMetadataProvider implements ILayoutMetaDataProvider {
  /**
   * Default value for {@link #REVERSE_INPUT}.
   */
  private static final boolean REVERSE_INPUT_DEFAULT = false;

  /**
   * True if nodes should be placed in reverse order of their
   * appearance in the graph.
   */
  public static final IProperty<Boolean> REVERSE_INPUT = new Property<Boolean>(
            "treelayoutgroupa.reverseInput",
            REVERSE_INPUT_DEFAULT,
            null,
            null);

  /**
   * Default value for {@link #USED_STRATEGY}.
   */
  private static final AlgorithmTypes USED_STRATEGY_DEFAULT = AlgorithmTypes.INORDER;

  /**
   * The currently used strategy. Take a look at the enum for more information.
   */
  public static final IProperty<AlgorithmTypes> USED_STRATEGY = new Property<AlgorithmTypes>(
            "treelayoutgroupa.usedStrategy",
            USED_STRATEGY_DEFAULT,
            null,
            null);

  public void apply(final org.eclipse.elk.core.data.ILayoutMetaDataProvider.Registry registry) {
    registry.register(new LayoutOptionData.Builder()
        .id("treelayoutgroupa.reverseInput")
        .group("")
        .name("Reverse Input")
        .description("True if nodes should be placed in reverse order of their appearance in the graph.")
        .defaultValue(REVERSE_INPUT_DEFAULT)
        .type(LayoutOptionData.Type.BOOLEAN)
        .optionClass(Boolean.class)
        .targets(EnumSet.of(LayoutOptionData.Target.PARENTS))
        .visibility(LayoutOptionData.Visibility.VISIBLE)
        .create()
    );
    registry.register(new LayoutOptionData.Builder()
        .id("treelayoutgroupa.usedStrategy")
        .group("")
        .name("Used strategy")
        .description("The currently used strategy. Take a look at the enum for more information.")
        .defaultValue(USED_STRATEGY_DEFAULT)
        .type(LayoutOptionData.Type.ENUM)
        .optionClass(AlgorithmTypes.class)
        .targets(EnumSet.of(LayoutOptionData.Target.PARENTS))
        .visibility(LayoutOptionData.Visibility.VISIBLE)
        .create()
    );
    new treelayoutgroupa.options.TreelayoutgroupaOptions().apply(registry);
  }
}
