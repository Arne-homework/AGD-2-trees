package treelayoutgroupa;

import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkNode;

public abstract class OurAlgorithms {
    
    /**
     * The root of the graph.
     */
    public ElkNode layoutGraph;
    /**
     * The monitor to log the progress of the algorithm.
     */
    public IElkProgressMonitor progressMonitor;
    /**
     * The monitor to log the placing of the nodes.
     */
    public IElkProgressMonitor nodePlacingMonitor;
    
    /**
     * Abstract class constructor to use the strategy pattern.
     * 
     * @param layoutGraph The root of the graph.
     * @param progressMonitor The monitor to log the progress of the algorithm.
     * @param nodePlacingMonitor The monitor to log the placing of the nodes.
     */
    public OurAlgorithms(ElkNode layoutGraph, IElkProgressMonitor progressMonitor, IElkProgressMonitor nodePlacingMonitor) {
        progressMonitor = this.progressMonitor;
        layoutGraph = this.layoutGraph;
        nodePlacingMonitor = this.nodePlacingMonitor;
    }
    
}
