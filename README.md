# Regenerating Blocks

Regenerating Blocks is a cross-loader Minecraft mod for **Fabric** and **Forge** that adds a special block which can regenerate into another block after a delay.

## Features

- A regenerating block that can be configured to restore a target block
- Timer-based regeneration
- Block/item tooltip information
- Command support for setting up regenerating blocks
- Jade integration for extra block info where available

## Commands

The mod adds a command for creating a correctly configured regenerating block item:

```text
/regeneratingblocks <target> <block> <timer>
```

- `<target>` — one or more players who should receive the item
- `<block>` — the block that this regenerating block will restore
- `<timer>` — regeneration time in ticks, minimum `1`

Example:

```text
/regeneratingblocks @p minecraft:diamond_ore 1200
```

This gives the selected player a regenerating block item with the correct `TargetBlock` and `Timer` NBT values already set.

## Global regenerating blocks via tags

You can also make any block a globally regenerating block by adding it to a block tag whose path starts with `regenerating/`.

The mod reads tags in the following format:

```text
data/regeneratingblocks/tags/blocks/regenerating/<timer>.json
```

Where:

- `regeneratingblocks` is the mod namespace
- `<timer>` is the regeneration time in ticks
- the JSON file contains the blocks that should use that timer

Example file: `data/regeneratingblocks/tags/blocks/regenerating/1200.json`

```json
{
  "replace": false,
  "values": [
	"minecraft:diamond_ore",
	"minecraft:deepslate_diamond_ore"
  ]
}
```

Any block included in that tag will be treated as a regenerating block with a `1200` tick delay when broken.

## Configuration

Shared config lives in `common` and currently includes:

- `defaultTimer` — default regeneration time in ticks
- `defaultBlock` — fallback block used when no target block is provided

The config is loaded through the shared `ConfigInit` in `common`, so it does not depend on the loader side.

## Dependencies

The mod uses:

- **Architectury API** for cross-loader logic
- **ShadowConfig** for configuration
- **Jade** for optional block info display