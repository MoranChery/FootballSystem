package Data;

import Controller.BaseEmbeddedSQL;
import Model.Enums.RoleType;
import Model.Enums.Status;
import Model.Role;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleDbInServerTest extends BaseEmbeddedSQL {
    private RoleDb roleDb = RoleDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private TeamDb teamDb = TeamDbInServer.getInstance();

    @Before
    public void init() throws SQLException {
        teamDb.deleteAll();
        roleDb.deleteAll();
        subscriberDb.deleteAll();
    }

    @Test
    public void testInsertRole() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        RoleType roleType = RoleType.PLAYER;
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, roleType);

        Role role = roleDb.getRole(emailAddress);
        Assert.assertEquals(teamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());
    }

    @Test
    public void testInsertRoleError() {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        try {
            roleDb.insertRole(null, teamName, RoleType.PLAYER);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("bad input", e.getMessage());
        }

        try {
            roleDb.insertRole(emailAddress, teamName, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testCreateRoleInSystem() throws Exception {
        String emailAddress = "user@email.com";
        RoleType roleType = RoleType.PLAYER;
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        roleDb.createRoleInSystem(emailAddress, roleType);

        Role role = roleDb.getRole(emailAddress);
        Assert.assertNull(role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());
    }

    @Test
    public void testCreateRoleInSystemError() {
        String emailAddress = "user@email.com";
        try {
            roleDb.createRoleInSystem(null, RoleType.PLAYER);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("bad input", e.getMessage());
        }

        try {
            roleDb.createRoleInSystem(emailAddress, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetRoles() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));

        List<Role> roles = roleDb.getRoles(emailAddress);
        Assert.assertTrue(roles.isEmpty());

        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, RoleType.TEAM_OWNER);
        roleDb.insertRole(emailAddress, teamName, RoleType.TEAM_MANAGER);

        roles = roleDb.getRoles(emailAddress);
        Assert.assertEquals(2, roles.size());

        List<RoleType> roleTypes = roles.stream().map(Role::getRoleType).collect(Collectors.toList());
        Assert.assertTrue(roleTypes.contains(RoleType.TEAM_OWNER));
        Assert.assertTrue(roleTypes.contains(RoleType.TEAM_MANAGER));

        Set<String> teams = roles.stream().map(Role::getTeamName).collect(Collectors.toSet());
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.contains(teamName));
    }

    @Test
    public void testRemoveRoleFromTeam() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        RoleType roleType = RoleType.PLAYER;
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, roleType);

        Role role = roleDb.getRole(emailAddress);
        Assert.assertEquals(teamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());

        roleDb.removeRoleFromTeam(emailAddress, teamName, roleType);

        role = roleDb.getRole(emailAddress);
        Assert.assertNull(role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());
    }

    @Test
    public void testRemoveRole() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        RoleType roleType = RoleType.PLAYER;
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, roleType);

        Role role = roleDb.getRole(emailAddress);
        Assert.assertEquals(teamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());

        roleDb.removeRole(emailAddress, roleType);

        List<Role> roles = roleDb.getRoles(emailAddress);
        Assert.assertTrue(roles.isEmpty());
    }

    @Test
    public void testRemoveRoleError() {
        String emailAddress = "user@email.com";
        try {
            roleDb.removeRole(null, RoleType.PLAYER);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }

        try {
            roleDb.removeRole(emailAddress, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void testRemoveRoleNotFound() {
        String emailAddress = "not.found@email.com";
        try {
            roleDb.removeRole(emailAddress, RoleType.PLAYER);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("emailAddress not found", e.getMessage());
        }
    }

    @Test
    public void testGetRole() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        RoleType roleType = RoleType.PLAYER;

        // no roles - default fan
        Role role = roleDb.getRole(emailAddress);
        Assert.assertEquals(role.getRoleType(), RoleType.FAN);
        Assert.assertNull(role.getTeamName());
        Assert.assertNotNull(role.getAssignedDate());

        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, roleType);

        role = roleDb.getRole(emailAddress);
        Assert.assertEquals(teamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());
    }

    @Test
    public void testUpdateTeam() throws Exception {
        String emailAddress = "user@email.com";
        String teamName = "my-team";
        RoleType roleType = RoleType.PLAYER;
        subscriberDb.insertSubscriber(buildSubscriber(emailAddress));
        teamDb.insertTeam(teamName);
        roleDb.insertRole(emailAddress, teamName, roleType);

        Role role = roleDb.getRole(emailAddress);
        Assert.assertEquals(teamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());

        // new team
        String anotherTeamName = "another-my-team";
        teamDb.insertTeam(anotherTeamName);

        // update team
        roleDb.updateTeam(anotherTeamName, emailAddress);

        role = roleDb.getRole(emailAddress);
        Assert.assertEquals(anotherTeamName, role.getTeamName());
        Assert.assertEquals(roleType, role.getRoleType());
        Assert.assertNotNull(role.getAssignedDate());
    }

    private Subscriber buildSubscriber(String email) {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(1);
        subscriber.setEmailAddress(email);
        subscriber.setFirstName("firstName");
        subscriber.setLastName("lastName");
        subscriber.setPassword("password");
        subscriber.setStatus(Status.ONLINE);
        return subscriber;
    }
}
