# SSPerm
Basic permissions plugins with developers in mind.

I created this permissions plugin simply because I wanted to, not because of any request.
It came out great and I will probably use this rather than any other plugin if I ever decide to make a server.

---
While making this plugin I mainly focused on the developer side, meaning hooking into the plugin and managing everything is extremely simple.  
You have `SSPerm.getAPI()` to get started, and from there you can get the `GroupManager` or `PlayerManager` to manage all aspects of the plugin.

How to Use
---
### Permissions

<table>
    <tr>
        <td><b>Permission</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td>ssperm.*</td>
        <td>Permission for everything.</td>
    </tr>
    <tr>
        <td>ssperm.manage</td>
        <td>Permission to manage players and groups. (Does not give .admin)</td>
    </tr>
    <tr>
        <td>ssperm.manage.admin</td>
        <td>Permission to create/remove groups.</td>
    </tr>
    <tr>
        <td>ssperm.manage.reload</td>
        <td>Permission to reload the plugin.</td>
    </tr>
    <tr>
        <td>ssperm.manage.groups</td>
        <td>Permission to manage groups.</td>
    </tr>
    <tr>
        <td>ssperm.manage.players</td>
        <td>Permission to manage players.</td>
    </tr>
</table>

### Ingame CMDs
/ssperm is the main command, with /ssp as an alias.  
The format is `/ssp <section> <name> [<setting> <value>]`  
I will reference the values in the angle brackets to explain the subcommands.  
For every section and action, you may just use the first letter of the word rather than typing the entire thing if you like.

**Section**  
The starting point for all subcommands.

<table>
    <tr>
        <td><b>Section</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td>help</td>
        <td>Get help info (based on what permissions you have)</td>
    </tr>
    <tr>
        <td>list</td>
        <td>List all groups.</td>
    </tr>
    <tr>
        <td>create</td>
        <td>Create a group named <code>&lt;name&gt;</code></td>
    </tr>
    <tr>
        <td>remove</td>
        <td>Remove the group named <code>&lt;name&gt;</code></td>
    </tr>
    <tr>
        <td>group</td>
        <td>View info about group <code>&lt;name&gt;</code>. Or modify <code>&lt;setting&gt;</code> for the group.</td>
    </tr>
    <tr>
        <td>player</td>
        <td>View info about player <code>&lt;name&gt;</code> (If they are online). Or modify <code>&lt;setting&gt;</code> for the player.</td>
    </tr>
    <tr>
        <td>reload</td>
        <td>Reload the plugin. Any changes made in the configs will take effect.</td>
    </tr>
</table>

Examples:  
`/ssp group default` will show info for default group.  
`/ssp create admin` will create a group named admin.  
Examples with shorthand:  
`/ssp r mod` will remove the group named mod.  
`/ssp l` will list all groups.

**Group Actions**  
Modify `<setting>` for group `<name>`

<table>
    <tr>
        <td><b>Action</b></td>
        <td><b>Expected Value</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td><code>&lt;none&gt;</code></td>
        <td>Nothing</td>
        <td>Leave the action and value blank to view group info.</td>
    </tr>
    <tr>
        <td>add</td>
        <td>Permission</td>
        <td>Add permission <code>&lt;value&gt;</code> to the group. (Prefix with "-" to take away permission)</td>
    </tr>
    <tr>
        <td>remove</td>
        <td>Permission</td>
        <td>Remove permission <code>&lt;value&gt;</code> from the group. (Include "-" if you added that)</td>
    </tr>
    <tr>
        <td>inherit</td>
        <td>Group name</td>
        <td>Inherit the permissions of group <code>&lt;value&gt;</code> (recursive)</td>
    </tr>
    <tr>
        <td>uninherit</td>
        <td>Group name</td>
        <td>Stop inheriting group <code>&lt;value&gt;</code></td>
    </tr>
    <tr>
        <td>prefix</td>
        <td>String</td>
        <td>Set the group prefix to <code>&lt;value&gt;</code> (<code>_</code> for space, <code>\_</code> for literal underscore, set to <code>{none}</code> for no prefix)</td>
    </tr>
    <tr>
        <td>suffix</td>
        <td>String</td>
        <td>Set the group suffix to <code>&lt;value&gt;</code> (<code>_</code> for space, <code>\_</code> for literal underscore, set to <code>{none}</code> for no suffix)</td>
    </tr>
    <tr>
        <td>namecolor (nc)</td>
        <td>Any color code (a-f 0-9)</td>
        <td>Set the group namecolor to <code>&lt;value&gt;</code> (default: f)</td>
    </tr>
    <tr>
        <td>nameformat (nf)</td>
        <td>Any format code (k-o and r)</td>
        <td>Set the group nameformat to <code>&lt;value&gt;</code> (default: r)</td>
    </tr>
    <tr>
        <td>chatcolor (cc)</td>
        <td>Any color code (a-f 0-9)</td>
        <td>Set the group chatcolor to <code>&lt;value&gt;</code> (default: f)</td>
    </tr>
    <tr>
        <td>chatformat (cf)</td>
        <td>Any format code (k-o and r)</td>
        <td>Set the group chatformat to <code>&lt;value&gt;</code> (default: r)</td>
    </tr>
</table>

Examples:  
`/ssp group default add bukkit.command.list` adds bukkit.command.list to default group.  
`/ssp group default prefix &bUser_` sets the prefix for default group to User in aqua with a space after.  
Examples with shorthand:  
`/ssp g default nc e` sets the namecolor for players in this group to yellow.  
`/ssp g mod i default` makes the group mod inherit the permissions of default.

**Player Actions**  
Modify `<setting>` for player `<name>` Player options (prefix, namecolor, etc.) will be used instead of group options if they are set.

<table>
    <tr>
        <td><b>Action</b></td>
        <td><b>Expected Value</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td><code>&lt;none&gt;</code></td>
        <td>Nothing</td>
        <td>Leave the action and value blank to view player info.</td>
    </tr>
    <tr>
        <td>add</td>
        <td>Permission</td>
        <td>Add permission <code>&lt;value&gt;</code> to the player. (Prefix with "-" to take away permission)</td>
    </tr>
    <tr>
        <td>remove</td>
        <td>Permission</td>
        <td>Remove permission <code>&lt;value&gt;</code> from the player. (Include "-" if you added that)</td>
    </tr>
    <tr>
        <td>move</td>
        <td>Group name</td>
        <td>Move this player to group <code>&lt;value&gt;</code></td>
    </tr>
    <tr>
        <td>prefix</td>
        <td>String</td>
        <td>Set the player's prefix to <code>&lt;value&gt;</code> (<code>_</code> for space, <code>\_</code> for literal underscore, set to <code>{none}</code> for no prefix)</td>
    </tr>
    <tr>
        <td>suffix</td>
        <td>String</td>
        <td>Set the player's suffix to <code>&lt;value&gt;</code> (<code>_</code> for space, <code>\_</code> for literal underscore, set to <code>{none}</code> for no suffix)</td>
    </tr>
    <tr>
        <td>namecolor (nc)</td>
        <td>Any color code (a-f 0-9)</td>
        <td>Set the player's namecolor to <code>&lt;value&gt;</code> (default: f)</td>
    </tr>
    <tr>
        <td>nameformat (nf)</td>
        <td>Any format code (k-o and r)</td>
        <td>Set the player's nameformat to <code>&lt;value&gt;</code> (default: r)</td>
    </tr>
    <tr>
        <td>chatcolor (cc)</td>
        <td>Any color code (a-f 0-9)</td>
        <td>Set the player's chatcolor to <code>&lt;value&gt;</code> (default: f)</td>
    </tr>
    <tr>
        <td>chatformat (cf)</td>
        <td>Any format code (k-o and r)</td>
        <td>Set the player's chatformat to <code>&lt;value&gt;</code> (default: r)</td>
    </tr>
</table>

Examples:  
`/ssp player Steve add -bukkit.command.plugins` adds a negative permission to Steve (so he can't do /pl)  
`/ssp player Steve move mod` moves Steve into mod group.  
Examples with shorthand:  
`/ssp p Steve nf l` sets Steve's nameformat to bold.  
`/ssp p Steve cc a` sets Steve's chatcolor to green.

Planned Updates
---
These are things I plan to add in future updates:
* Ability to do stuff with players that are offline.
* Pressing tab in chat to quickly type commands.

Implemented Updates
---
These are updates that have been added since initial release:  
**Version 1.1.0:**
* Ability to change the chat format.
* Finish the `reload()` method. (Currently only reloads configs, not actual group objects)

If you have any suggestions, contact me somehow or make a PR.
