pipelineJob('madcore.plugin.slack.delete') {
    parameters {
	    stringParam('APP_NAME', 'slack', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Docker: registry delete'
                build job: 'madcore.docker.registry.delete', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Docker: registry status'
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: delete secret'
                build 'madcore.kubectl.delete.secret', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: delete'
                build 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', 'slack/kub')]
                }
            """.stripIndent())
	    }
    }
}
