# SSPermissions
Basic permissions plugins with developers in mind.

Developers in mind means that hooking into the plugin is extremely simple.  
You have `SSPermissions.getAPI()` to get started, and from there you can get the `GroupManager` or `PlayerManager` to manage all aspects of the plugin.

How to Use
---
### Permissions

<table>
    <tr>
        <td><b>Permission</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td>ssp.*</td>
        <td>Permission for everything.</td>
    </tr>
    <tr>
        <td>ssp.manage</td>
        <td>Permission to manage players and groups. (Does not give .admin or .reload)</td>
    </tr>
    <tr>
        <td>ssp.manage.admin</td>
        <td>Permission to create/remove groups.</td>
    </tr>
    <tr>
        <td>ssp.manage.reload</td>
        <td>Permission to reload the plugin.</td>
    </tr>
    <tr>
        <td>ssp.manage.groups</td>
        <td>Permission to manage groups.</td>
    </tr>
    <tr>
        <td>ssp.manage.players</td>
        <td>Permission to manage players.</td>
    </tr>
</table>

### Ingame CMDs
/ssp is the main command   
The format is `/ssp <section> <name> [<setting> <value>]`  
I will use the names of the words in angle brackets to explain the commands.  
For every section and setting, you may just use the first letter of the word rather than typing the entire thing if you like.

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
        <td>format</td>
        <td>Change the global chat format. You must include <code>&lt;player&gt;</code> and <code>&lt;msg&gt;</code> to update the format.</td>
    </tr>
    <tr>
        <td>reload</td>
        <td>Reload the plugin. Any changes made in the configs will take effect.</td>
    </tr>
</table>

Examples:  
`/ssp group default` will show info for default group.  
`/ssp create admin` will create a group named admin.  
`/ssp player StarShadow` will show basic info for StarShadow.  
Examples with shorthand:  
`/ssp r mod` will remove the group named mod.  
`/ssp l` will list all groups.  
`/ssp f <group> <player> -> <msg>` will update the chat format to show the group a player is in.

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
        <td>format</td>
        <td>String</td>
        <td>Set the chat format for this group. Must include <code>&lt;player&gt;</code> and <code>&lt;msg&gt;</code> to be valid.</td>
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
`/ssp group admin uninherit default` will have admin no longer inherit default group.  
Examples with shorthand:  
`/ssp g default nc e` sets the namecolor for players in this group to yellow.  
`/ssp g mod i default` makes the group mod inherit the permissions of default.  
`/ssp g default r bukkit.command.plugins`  will remove bukkit.command.plugins from the default group.

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
        <td>format</td>
        <td>String</td>
        <td>Set the chat format for this player. Must include <code>&lt;player&gt;</code> and <code>&lt;msg&gt;</code> to be valid.</td>
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
`/ssp player Steve suffix Level_5` will set Steve's suffix to "Level 5"  
Examples with shorthand:  
`/ssp p Steve nf l` sets Steve's nameformat to bold.  
`/ssp p Steve cc a` sets Steve's chatcolor to green.  
`/ssp p Steve p Friends` sets Steve's prefix to "Friends"

Planned Updates
---
These are things I plan to add in future updates:
* ~~Ability to set chat format per player/group~~ (Added in version 2.0.0)
* ~~Ability to do stuff with players that are offline.~~ (Added in version 2.0.0)
