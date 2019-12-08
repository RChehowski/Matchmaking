#Matchmaking

Okay, I'm *not* a high-load backend developer to any degree. 
Anyway, this is my brave attempt to make a scalable implementation of online matchmaking for online games.

# The problem
The service needs to handle `{appDomain}/addUser?name=[name]&skill=[double]&latency=[double]` requests, adding new players to the polling queue.
The poller wakes up every **X** seconds and automatically retrieves **N** players from the queue and sets them together to the group calculating some metrics.
The group then got printed to the `stdout`.


# The approach
I'm pretty sure that it was a good idea to combine the entire list of player parameters such as `[strength, endurance, resistance, experience, win_rate]` into the one named `skill`.
`latency`, however, does not depend on `skill`, so we better not combine them into one.
So if we had **two** parameters and we need to look through them, the best possible and the most obvious *for me* way is to construct a lookup structure, where it will be easy to perform such operation.
By the way, one may use a simple \sqrt{((x<sub>1</sub>-x<sub>2</sub>)<sup>2</sup>+(y<sub>1</sub>-y<sub>2</sub>)<sup>2</sup>)} formula to calculate the distance between two points, but this will require *O*(N<sup>2</sup>) operation, where **N** is the number of players in the queue.

> The optimized version of this brute approach may work as fast as *O*(N), but it will require some technique, such as the [card table](https://msdnshared.blob.core.windows.net/media/TNBlogsFS/BlogFileStorage/blogs_msdn/abhinaba/WindowsLiveWriter/BackToBasicsGenerationalGarbageCollectio_115F4/image_18.png). To compute the load amount of a certain amount of space. 

As for the experienced *game engine programmer*, I know that [Quad Trees](https://en.wikipedia.org/wiki/Quadtree) as a special case of [KD-trees](https://en.wikipedia.org/wiki/K-d_tree) could be used on such purpose.

> One may also use **N**-dimensional trees to manage **N**-dimensional points placed in cubes or tesseracts instead of 2D surfaces.

The idea is to construct a quad tree and connect a simple atomic counter to each QuadTree node, representing the amount of records in the certain part of 2D space.
Then you may simply compare counter values to determine the most load part of space to take from that part first, this will solve the problem of balancing.

The approaches that could be used further to find other players:
1. [The nearest neighbor algorithm](https://ericandrewlewis.github.io/how-a-quadtree-works/) might be used to find **K** geometically closest points in the quad-tree. This **increases** lookup quality, but also **increases** search time and makes concurrent lookup more difficult.
2. 
