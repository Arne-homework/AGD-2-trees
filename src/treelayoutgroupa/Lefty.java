package treelayoutgroupa;

import java.util.List;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkNode;

public class Lefty {
    
    private IElkProgressMonitor progressMonitor;
    private ElkNode layoutGraph;
    
    public Lefty(ElkNode layoutGraph, IElkProgressMonitor progressMonitor) {
        progressMonitor = this.progressMonitor;
        layoutGraph = this.layoutGraph;
    }

    public void lefty(List<ElkNode> nodes, ElkPadding padding, double currX, double currY, double nodeNodeSpacing, IElkProgressMonitor nodePlacingMonitor) {
        // TODO: Implement the actual algorithm in here!
        for (ElkNode node : nodes) {
            // Set the node's coordinates
            node.setX(currX);
            node.setY(padding.top);
            
            // Advance the coordinates
            currX += node.getWidth() + nodeNodeSpacing;
            currY = Math.max(currY, padding.top + node.getHeight());
            nodePlacingMonitor.log("currX: " + currX);
            nodePlacingMonitor.logGraph(layoutGraph, node.getIdentifier() + " placed");
        }
        
        if (!nodes.isEmpty()) {
            currX -= nodeNodeSpacing;
        }
    }
}
