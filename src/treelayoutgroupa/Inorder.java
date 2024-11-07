package treelayoutgroupa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import treelayoutgroupa.options.TreelayoutgroupaOptions;

public class Inorder {
    
    /**
     * The different states of a node.
     */
    private enum Status{
        FIRST_VISIT,
        LEFT_VISIT,
        REMAINING_CHILD_VISIT, // Case > 2 children.
        RIGHT_VISIT
    }
    
    /**
     * The actual Inorder implementation.
     * Based on Algorithm 2 in [Wetherell, Shannon 1979]
     * 
     * @param layoutGraph The current graph.
     * @param nodes A list of all nodes in the graph.
     * @param root The root node of the given graph.
     * @param nodePlacingMonitor A monitor for placing the nodes.
     * @param nodeNodeSpacing The spacing between nodes.
     */
    public void inorder(ElkNode layoutGraph, List<ElkNode> nodes, ElkNode root, IElkProgressMonitor nodePlacingMonitor) {
        // Retrieving the padding to reduce the number of necessary arguments.
        ElkPadding padding = layoutGraph.getProperty(TreelayoutgroupaOptions.PADDING);
        double nodeNodeSpacing = layoutGraph.getProperty(TreelayoutgroupaOptions.SPACING_NODE_NODE);
        
        // Declare and initialize necessary variables for the algorithm.
        // The next x-coordinate.
        int next_number = 1;
        // The current node, we want to start with the root.
        ElkNode current = root;
        // Map containing the (visited) status of each node.
        HashMap<ElkNode, Status> status = new HashMap<ElkNode, Status>();
        // Setting the status of the root.
        status.put(current, Status.FIRST_VISIT);
      
        // Let's start the actual algorithm.
        while (current != null) {
            // Let's get the children from left to right.
            EList<ElkEdge> outgoingEdges = current.getOutgoingEdges();
            EList<ElkNode> childrenList = new BasicEList<>();
            
            if (outgoingEdges.size()>0) {
                // There is at least one child!
                for (ElkEdge elkEdge : outgoingEdges) {
                    // (current -> child) => The child is the target of the outgoing edge. We only have 2-uniform hypergraph (each edge connects exactly 2 nodes)
                    ElkNode child = ElkGraphUtil.connectableShapeToNode(elkEdge.getTargets().get(0));
                    childrenList.add(child);
                }
            }
            
            // TODO: Problem: The right child does not get stored in the map!
            // Check, which status the current node is in.
            switch (status.get(current)) {
            
            case FIRST_VISIT:
                // In traversal order we want to visit the children from left, root to right.
                status.put(current, Status.LEFT_VISIT);
                // Check, whether there is at least one child.
                if (!childrenList.isEmpty()) {
                    // Let's visit the left (or only) child before the root.
                    current = childrenList.get(0);
                    status.put(current, Status.FIRST_VISIT);
                }
                break;
                
            case LEFT_VISIT:
                // Setting the node at (x,y) and adjust the coordinates.
                // Therefore, get the height of the current node.
                int currentLevel = current.getProperty(InternalProperties.NODE_LEVEL);
                nodePlacingMonitor.log("currX: " + next_number);
                
                current.setX(next_number);
                next_number++;
                // Use a multiple of the nodes height as y position.
                current.setY(currentLevel * (padding.top + current.getHeight()));
                
                // Check, if there are >2 children! The root will only be visited after children/2!
                if (childrenList.size() > 2) {
                    status.put(current, Status.REMAINING_CHILD_VISIT);
                } else {
                    // The next time visit the node right of us, even if that one is a layer above us.
                    status.put(current, Status.RIGHT_VISIT);
                }
                
                // But first let's visit our (remaining) children.
                if (!childrenList.isEmpty()) {
                    current = visitRemainingChild(current, childrenList, status);
                }
                break;
                
            case REMAINING_CHILD_VISIT:
                // TODO: This state is different. Check, whether it is actually working!
                // Let's visit our (remaining) children.
                ElkNode remainingChild = visitRemainingChild(current, childrenList, status);
                // Check, whether there is actually a child left.
                if (remainingChild != null) {
                    current = remainingChild;
                }
                break;
                
            case RIGHT_VISIT:
                // We backtrack to the parent node.
                // Hopefully there is at most one incoming edge.
                EList<ElkEdge> incommingEdges = current.getIncomingEdges();
                
                // We only have to go on, if there are still children.
                if (!incommingEdges.isEmpty()) {
                    ElkEdge incommingEdge = incommingEdges.get(0);
                    // (parent -> current) => The parent is the source of the incomming edge.
                    if (incommingEdge != null) {
                        current = ElkGraphUtil.connectableShapeToNode(incommingEdge.getSources().get(0));
                    }
                } else {
                    // Assuming the parent of the root is null.
                    current = null;
                }
                break;
                
                
            default:
                throw new IllegalArgumentException("Unexpected value: " + status.get(current));
            }
        }
    }
    
    /**
     * Visits the remaining children in case of a rose tree.
     * 
     * @param current The parent node.
     * @param childrenList The list of children nodes.
     * @param status The status of each node.
     * @return The next child to visit. Null, if there is none left.
     */
    private ElkNode visitRemainingChild(ElkNode current, EList<ElkNode> childrenList, HashMap<ElkNode, Status> status) {
     // But first let's visit our (remaining) children.
        if (!childrenList.isEmpty()) {
           for (ElkNode child : childrenList) {
               // TODO: Not all children are visited!
               if (!status.containsKey(child)) {
                   // This child has not been visited yet.
                   status.put(current, Status.FIRST_VISIT);
                   return child;
               }
           } 
        }
        return null;
    }

}