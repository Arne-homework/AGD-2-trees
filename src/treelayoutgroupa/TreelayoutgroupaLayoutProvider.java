package treelayoutgroupa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.elk.core.AbstractLayoutProvider;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.util.ElkGraphUtil;

import treelayoutgroupa.options.TreelayoutgroupaOptions;

/**
 * A simple layout algorithm class. This algorithm already supports a number of layout options, places nodes, and
 * routes edges.
 */
public class TreelayoutgroupaLayoutProvider extends AbstractLayoutProvider {

    @Override
    public void layout(ElkNode layoutGraph, IElkProgressMonitor progressMonitor) {
        // Start progress monitor
        progressMonitor.begin("Treelayoutgroupa", 2);
        progressMonitor.log("Algorithm began");
                
        // Retrieve several properties like padding and spacing
        ElkPadding padding = layoutGraph.getProperty(TreelayoutgroupaOptions.PADDING);
        
        double edgeEdgeSpacing = layoutGraph.getProperty(TreelayoutgroupaOptions.SPACING_EDGE_EDGE);
        double edgeNodeSpacing = layoutGraph.getProperty(TreelayoutgroupaOptions.SPACING_EDGE_NODE);
        double nodeNodeSpacing = layoutGraph.getProperty(TreelayoutgroupaOptions.SPACING_NODE_NODE);
        double maxHeight = layoutGraph.getHeight();
        
        // Get and possibly reverse the list of nodes to lay out
        List<ElkNode> nodes = new ArrayList<>(layoutGraph.getChildren());
        if (layoutGraph.getProperty(TreelayoutgroupaOptions.REVERSE_INPUT)) {
            Collections.reverse(nodes);
        }
        
        // Create a sub monitor for node placement
        IElkProgressMonitor nodePlacingMonitor = progressMonitor.subTask(1);
        nodePlacingMonitor.begin("Node Spacing", nodes.size());
        
        // Place the nodes
        double currX = padding.left;
        double currY = padding.top;
        
        // Make an output to the debug log
        nodePlacingMonitor.log("currX: " + currX);
        nodePlacingMonitor.logGraph(layoutGraph, "No node placed yet");
        
        /**
         * Contains the algorithm which shall be used.
         * Set the wanted algorithm here!
         */
        AlgorithmTypes currentAlgorithm = AlgorithmTypes.LEFTY;
        
        // Checks, which algorithm is selected and uses that one.
        switch(currentAlgorithm) {
            case LEFTY:
                Lefty leftyInstance = new Lefty(layoutGraph, progressMonitor, nodePlacingMonitor);
                leftyInstance.lefty(nodes, padding, currX, currY, nodeNodeSpacing, maxHeight);
                
            case INORDER:
                // TODO: Do something here!
        
        }

        // Close the sub monitor
        nodePlacingMonitor.done();
        progressMonitor.log("Node Placing done!");
        
        // Create sub monitor for edge routing
        IElkProgressMonitor edgeRoutingMonitor = progressMonitor.subTask(1);
        edgeRoutingMonitor.begin("Edge Routing", layoutGraph.getContainedEdges().size());
        edgeRoutingMonitor.logGraph(layoutGraph, "No edge routed yet");
        
        // Route the edges
        if (!layoutGraph.getContainedEdges().isEmpty()) {
            currY += edgeNodeSpacing;
            edgeRoutingMonitor.log("currY: " + currY);
            
            for (ElkEdge edge : layoutGraph.getContainedEdges()) {
                ElkNode source = ElkGraphUtil.connectableShapeToNode(edge.getSources().get(0));
                ElkNode target = ElkGraphUtil.connectableShapeToNode(edge.getTargets().get(0));
                
                ElkEdgeSection section = ElkGraphUtil.firstEdgeSection(edge, true, true);
                
                section.setStartLocation(
                        source.getX() + source.getWidth() / 2,
                        source.getY() + source.getHeight());
                section.setEndLocation(
                        target.getX() + target.getWidth() / 2,
                        target.getY() + target.getHeight());
                
                ElkGraphUtil.createBendPoint(section, section.getStartX(), currY);
                ElkGraphUtil.createBendPoint(section, section.getEndX(), currY);
                                
                currY += edgeEdgeSpacing;
                edgeRoutingMonitor.log("currY: " + currY);
                edgeRoutingMonitor.logGraph(layoutGraph, source.getIdentifier() + " -> " + target.getIdentifier());
            }
            
            currY -= edgeEdgeSpacing;
        }
        
        // Close the sub monitor
        edgeRoutingMonitor.done();
        
        progressMonitor.log("Edge Routing done!");
        
        // Set the size of the final diagram
        layoutGraph.setWidth(currX + padding.right);
        layoutGraph.setHeight(currY + padding.bottom);
        
        // End the progress monitor
        progressMonitor.log("Algorithm executed");
        progressMonitor.logGraph(layoutGraph, "Final graph");
        progressMonitor.done();
    }
}
