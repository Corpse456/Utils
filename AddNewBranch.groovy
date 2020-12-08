Map<String, String> env = System.getenv()
createBranch(env['_DB_ACCOUNT'])

static void createBranch(String accountName) {
    String[] text = "git branch -r".execute().text.split("\n")
    if (text.size() > 1) {
        List<String> branches = new ArrayList<>()
        for (int i = 1; i < text.size(); i++) {
            branches.add(text[i].replaceAll(" ", "").replace("origin/", ""))
        }
        if (!branches.contains(accountName)) {
            ("git checkout -b " + accountName).execute().text
            ("git push origin " + accountName).execute()
        }
    }
}
