This chatroom code is supposed to pair each two users that consecutively connect to the server into a private chatroom with each other.

Bugs include (but are not limited to):

The user's message are not actually passed to each other but rather simply displayed on the server.

If the first user sends the server messages before the second user connects, then those messages will be sent to the second user upon connection.

There is also no way to properly disconnect and upon any disconnection, the server will continously output "null".