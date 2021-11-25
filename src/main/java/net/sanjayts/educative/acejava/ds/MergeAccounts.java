package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
https://leetcode.com/problems/accounts-merge/

Good explanations at:

https://leetcode.com/problems/accounts-merge/solution/1131842
https://leetcode.com/problems/accounts-merge/discuss/109157/JavaC%2B%2B-Union-Find
 */
@Slf4j
public class MergeAccounts {

    public static void main(String[] args) {
        List<List<String>> accounts = Arrays.asList(
                Arrays.asList("David", "Avid0@m.co", "David0@m.co", "David1@m.co"),
                Arrays.asList("David", "Gvid3@m.co", "David3@m.co", "David4@m.co"),
                Arrays.asList("David", "David4@m.co", "David5@m.co"),
                Arrays.asList("David", "David2@m.co", "David3@m.co"),
                Arrays.asList("David", "David1@m.co", "David2@m.co")
        );
        MergeAccounts runner = new MergeAccounts();
        List<List<String>> out = runner.accountsMergeUnionFind(accounts);
        log.info("Accounts merge output with union find is {}", out);
        out = runner.accountsMergeDfs(accounts);
        log.info("Accounts merge output with DFS is {}", out);
    }

    /**
     * The intuition here is that we have groups of emails per user which *may* or may not be connected. The easiest way
     * to find it out is by maintaining a mapping of emails against their immediate neighbours. This will eventually
     * allow us to connect all relevant emails as we perform a DFS traversal starting with the very first email for a given
     * user. Another good thing worth noting is that we are guaranteed that users with matching emails will *always*
     * have the same name so no need to perform additional validations.
     *
     * In terms of critical touch-points for the algorithm, we need to take care of:
     * 1. Creating a graph of every email against its neighbours
     * 2. Maintain a mapping between the email and the name of its user
     * 3. Iterate over the unique emails in our graph and perform DFS to find all connected emails and add them against
     *    the name to which the email belongs to.
     *
     * Time complexity:
     *  Given `n` number of accounts with each account having a maximum of `m` elements, the worst case scenario would
     *  be having to sort the `n * m` list when all emails belong to the same person. Hence, its O(nmlog(nm))
     */
    public List<List<String>> accountsMergeDfs(List<List<String>> acts) {
        List<List<String>> res = new ArrayList<>();
        if (acts == null || acts.isEmpty()) {
            return res;
        }
        Map<String, String> mailToName = new HashMap<>();
        Map<String, HashSet<String>> graph = new HashMap<>();

        for (var account : acts) {
            String pName = account.get(0);
            for (int i = 1; i < account.size(); ++i) {
                String email = account.get(i);
                mailToName.put(email, pName);
                graph.putIfAbsent(email, new HashSet<>());
                if (i <= 1) continue;
                graph.get(email).add(account.get(i - 1));
                graph.get(account.get(i - 1)).add(email);
            }
        }

        Set<String> visited = new HashSet<>();
        for (var email : graph.keySet()) {
            // This check if very important to ensure that we don't insert multiple lists within our result list
            // all of which would be empty. This is because the size of our final result depends on the number of unique
            // users as opposed to the number of unique email ids.
            if (visited.contains(email)) {
                continue;
            }
            LinkedList<String> emailsForName = new LinkedList<>();
            dfs(email, visited, graph, emailsForName);
            Collections.sort(emailsForName);
            emailsForName.addFirst(mailToName.get(email));
            res.add(emailsForName);
        }
        return res;
    }

    private void dfs(String email, Set<String> visited, Map<String, HashSet<String>> graph, List<String> emails) {
        visited.add(email);
        emails.add(email);
        for (var ne : graph.get(email)) {
            if (visited.contains(ne)) {
                continue;
            }
            dfs(ne, visited, graph, emails);
        }
    }

    public List<List<String>> accountsMergeUnionFind(List<List<String>> acts) {
        List<List<String>> res = new ArrayList<>();
        if (acts == null || acts.isEmpty()) {
            return res;
        }
        Map<String, String> mailToOwner = new HashMap<>();
        Map<String, String> mailToParent = new HashMap<>();
        Map<String, TreeSet<String>> unions = new HashMap<>();

        for (var acc : acts) {
            for (int i = 1; i < acc.size(); ++i) {
                mailToParent.put(acc.get(i), acc.get(i));
                mailToOwner.put(acc.get(i), acc.get(0));
            }
        }
        for (var acc : acts) {
            String p = find(acc.get(1), mailToParent);
            for (int i = 2; i < acc.size(); ++i) {
                mailToParent.put(find(acc.get(i), mailToParent), p);
            }
        }
        for (var acc : acts) {
            String p = find(acc.get(1), mailToParent);
            if (!unions.containsKey(p)) {
                unions.put(p, new TreeSet<>());
            }
            for (int i = 1; i < acc.size(); ++i) {
                unions.get(p).add(acc.get(i));
            }
        }
        for (var e : unions.entrySet()) {
            var mails = new LinkedList<>(e.getValue());
            mails.addFirst(mailToOwner.get(e.getKey()));
            res.add(mails);
        }
        return res;
    }

    private String find(String p, Map<String, String> mailToParent) {
        return p == mailToParent.get(p) ? p : find(mailToParent.get(p), mailToParent);
    }

}
