"""
 Script to generate an elk graph from all the static html elements of www.google.com 
"""
import requests
from lxml import html


# url to process
URL = "https://www.google.com"

def load_page(url=URL):
    """ Load the text of a webpage
    """
    return requests.get(url).text


def doc_from_string(string=None):
    """generate ab etree docment from a string
    """
    string = load_page() if string is None else string
    return html.document_fromstring(string)

class Graph:
    """ intermediate graph datastructure
    """
    def __init__(self):
        self.nodes = []
        self.edges = []

def _handle_node(node_entry, graph, counter):
    """ process a node (node_entry) into the graph

    runs recursively over all subnodes of the etree.
    """
    node_i, tag, node = node_entry
    
    for child in node:
        child_i = counter[-1] + 1
        counter[-1] = child_i
        child_entry = (child_i, child.tag, child)
        graph.edges.append((node_i,child_i))
        graph.nodes.append(child_entry)
        _handle_node(child_entry, graph, counter)

def graph_from_doc(doc=None):
    """ create a graph from an etree document
    """
    doc = doc_from_string() if doc is None else doc
    graph = Graph()
    root_node = (1, doc.tag, doc)
    counter = [1]
    graph.nodes.append(root_node)
    _handle_node(root_node, graph, counter)
    return graph


def write_graph(filename, graph):
    """ writes a graph to the file in the etree format
    """
    with open(filename,"w") as  f:
        f.write("""algorithm:  Treelayoutgroupa
usedStrategy: INORDER

""")
        for i, tag, _ in graph.nodes:
            f.write(f"node n{i} {{ label \"{tag}\"}}\n")
        f.write("\n\n\n")
        for parent, child in graph.edges:
            f.write(f"edge n{parent} -> n{child}\n")




write_graph("google_elements.elkt", graph_from_doc(doc_from_string(load_page(URL))))
