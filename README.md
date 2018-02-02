# Idea

The idea behind this project is to extract activity-context pairs (for e.g. playing with a ball) that correspond to places and landmarks. After you've done that, you can search for places by the activities that you want to do.

For example you can expect to find the activity context pair:  have - picnic in say Borisova Gradina (Sofia).

# How it works

We crawled [TripAdvisor](https://www.tripadvisor.com), a site that contains landmarks and allows users to write text reviews for them. Specifically we crawled the top 6 tourist cities in Bulgaria and got all of their landmarks. The total number of text reviews we extracted is around 44 000. The text reviews are key to this project. Users tend to write what they've done while being at a place and we rely on that.

## Implementation
We used the Stanford CoreNLP library to build a dependency parse tree for every sentence in every text review. Then we traverse the parse tree and look for verbs that are directly connected to direct objects or adjuncts. We consider the verb as the "activity" and the direct object/adjunct as the context.

We do a sentiment analysis on every sentence, and only if the sentiment is non negative do we parse the sentence. The idea behind this is that if the user has written a negative review and has described some activities, you probably don't want to do them. For example this way we can distinguish between "an awful place to have a picnic" and "a great place to have a picnic".

Finally, we use WordNet to expand queries. In our GUI we allow free text queries, which we parse using the same method used for the text queries. However if we cannot find a match in the indexed places we try replacing either the verb or the context or both from the query with some of their synonyms.

# How to run

You can run it by executing the <code>main</code> method from the class <code>gui.fxgui.Main</code>. Note it takes a little bit of time to start.
