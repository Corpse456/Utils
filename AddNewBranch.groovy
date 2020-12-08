Map<String, String> env = System.getenv()
createBranch(env['_DB_ACCOUNT'])

static void createBranch(String accountName) {
    String[] text = "git branch -r".execute().text.split("\n")
    if (text.size() > 1) {
        List<String> branches = new ArrayList<>()
        for (int i = 1; i < text.size(); i++) {
            branches.add(text[i].replaceAll(" ", "").replace("origin/", ""))
        }
        if (branches.contains(accountName)) {
            print (("git branch -d " + accountName).execute().text)
            print (("git push origin --delete " + accountName).execute().text)
        }
    }
}
