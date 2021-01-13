# Practice4_Authentication_Authorization

Using *Practice 2 Solution* the following functionality must be added:

1. Use https connection through an autosigned certificate
2. There must be 3 kinds of users 
```ADMINISTRATORS, REGISTERED_USERS and UNREGISTERED_USERS```
3. Any action could be performed by *administrator users*.
4. *Authenticated users* could perform the following actions:
   - Create, read and delete comments
   - Modify user data
5. *Unregistered users* could perform the following action:
   - Create its own user.
   - Get the list of books (only id and title) 
   
## Notes
* *Administrator users* could be stored in the database  in the users table. In order to differ which user is administrator or not, a new boolean column _admin_ could be created.
*ONLY* administrator users could create users with admin flag to true.
 
* Endpoints needed for the administration of books will be only available for *administrator users*
* When a book is to be deleted, if it contains commentaries they should be deleted as well.

* All the previously described functionality will be implemented in JAVA and Node.
