# k3s 

## Install (Quickstart) - 1 Node Cluster

```
curl -sfL https://get.k3s.io | sh -
```

## Flannel 

  * Kann keine NetworkPolicies
  * Angelegte NetzwerkPolicies rennen ins Leere
  * Flannel läuft nicht als Pod, sondenr ist in der k3s - binary drin 

## Wenn Netzwerkpolicies dann z.B. calico install 

  * https://docs.tigera.io/calico/latest/getting-started/kubernetes/k3s/quickstart
