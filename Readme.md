### Matchmaking

Okay, I'm *not* a high-load backend developer to any degree. 
Anyway, this is my brave attempt to make a scalable implementation of the online matchmaking for online games.

### The problem
The service needs to handle `{appDomain}/addUser?name=[name]&skill=[double]&latency=[double]` requests, adding new players to the polling queue.
The poller wakes up every **X** seconds and automatically retrieves **N** players from the queue to set them together to the group calculating some metrics.
The group then got printed to the `stdout`.


### The approach
I'm pretty sure that it was a good idea to combine the entire list of sophisticated player parameters such as `[strength, endurance, resistance, experience, win_rate]` into the one named `skill`.
`latency`, however, does not depend on `skill`, so we'd better not combine them into one.
So if we had **two** parameters and we need to look through them, the best possible and the most obvious *for me* way is to construct a lookup structure, where it will be easy to perform such operation.
By the way, one may use a simple `sqrt(`(x<sub>1</sub>-x<sub>2</sub>)<sup>2</sup>+(y<sub>1</sub>-y<sub>2</sub>)<sup>2</sup>`)` formula to calculate the distance between two points, but this will require as many as **O**(N<sup>2</sup>) operations, where **N** is the amount of players enqueued.

> The optimized version of this brute approach may work as fast as *O*(N), but it will require extra memory to implement some technique, such as the [card table](https://msdnshared.blob.core.windows.net/media/TNBlogsFS/BlogFileStorage/blogs_msdn/abhinaba/WindowsLiveWriter/BackToBasicsGenerationalGarbageCollectio_115F4/image_18.png) to look through data to know the load weight of a certain part of space. 

As for the experienced *game engine programmer*, I know that [Quad Trees](https://en.wikipedia.org/wiki/Quadtree) as the special case of [KD-trees](https://en.wikipedia.org/wiki/K-d_tree) could be used on such purpose.

> One may also use **N**-dimensional trees to manage **N**-dimensional points placed in cubes or tesseracts instead of 2D surfaces.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Point_quadtree.svg/1024px-Point_quadtree.svg.png" alt="drawing" width="400"/>

The idea is to construct a quad tree and connect a simple atomic counter to each its node, representing the amount of records in the certain part of 2D space.
Then you may simply compare counter values to determine the most load part of space to take from that part first to make results more uniform.

The approaches that could be used further to find other players:
1. [The nearest neighbor algorithm](https://ericandrewlewis.github.io/how-a-quadtree-works/) might be used to find **K** geometrically closest points in the quad-tree. This **increases** lookup quality, but also **increases** search time and makes concurrent lookup more difficult.
2. Simply determine the most load part of the tree and then take items from it turning back to another non-empty sub-nodes when current space is purged. This allows to collect data within certain precision range (`1f` for this example). This approach was chosen for the demo. 

### Pros and cons
- **[+]** Making groups is as fast as **O**(log(**N**)) with the worst case of **O**(log(**N**)<sup>2</sup>). The worst case is the case when **N** elements are uniformly scattered along the whole tree, and the matchmaker needs to form a group of N players, but this case is rare, because usually the entire tree load is much greater than the size of one group.
- **[+]** This solution is very scalable and it can operate huge data sets. This is achieved because of the constant insert speed and predictable search speed.
- **[+]** It adapts to higher loads well. The **MORE** entries with similar properties the quad tree takes - the **FASTER** the lookup is.
- **[+]** It’s all thread-safe and lock-free. Entries can be placed concurrently via the multi-threaded server and player groups can be polled in the same time
- **[-]** It does not care about how many time player spent in the queue. However it could be managed by either adding matchmaker generations (a set of trees) or by counting age of the part of space. The system requires some extra parameters to be fine-tuned and thus is not implemented at the current state of the art.
- **[-]** Jetty as the web-server isn’t probably a good choice. By the way it can perfectly handle requests and could be easily replaced with another web-server since the bridge between the jetty and the matchmaker is sensible and thick.

### Conclusion
I believe that spartial approach is the best possible way to manage such structures.
Although my solution did not implemented all possible optimizations it has no sensible weak architectural points and all these optimizations could be added in future.