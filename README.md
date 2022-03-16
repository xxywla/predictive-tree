# predictive-tree
Reimplement paper Predictive Tree: An Efficient Index for Predictive Queries On Road Networks.

The structure is following:

![structure](https://raw.githubusercontent.com/xxywla/predictive-tree/master/src/main/resources/structure.png)

Predictive-tree construction algorithm is following:

![alg1](https://raw.githubusercontent.com/xxywla/predictive-tree/master/src/main/resources/alg1.png)

Predictive-tree maintenance algorithm is following:

![alg2](https://raw.githubusercontent.com/xxywla/predictive-tree/master/src/main/resources/alg2.png)

## Steps
### One
Get data, road network graph G(N,E,W), where N is the set of nodes, E is the set of edges, and W is the edge weights.

N and E can directly obtain from OSM, how about W ?